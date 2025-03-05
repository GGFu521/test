package com.xxx.modules.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.modules.entity.Environment;
import com.xxx.modules.entity.Place;
import com.xxx.modules.entity.Setting;
import com.xxx.modules.mapper.EnvironmentMapper;
import com.xxx.modules.mapper.PlaceMapper;
import com.xxx.modules.mapper.SettingMapper;
import com.xxx.modules.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

@Configuration
@IntegrationComponentScan
@Slf4j
public class MqttConfig {
    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout; // 连接超时








    /**
     * 创建MQTT连接信息
     * @return
     */
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{hostUrl});
        mqttConnectOptions.setMaxInflight(50);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(60);
        return mqttConnectOptions;
    }


    /**
     * MQTT推送消息客户端连接池创建并注入连接信息
     * @return
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
        //return factory;
    }


    public MessageHandler createMqttOutbound() {
        String tempId = MqttAsyncClient.generateClientId();
        MyMqttPahoMessageHandler messageHandler = new MyMqttPahoMessageHandler(clientId + tempId,
            mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        messageHandler.setDefaultQos(0);
        messageHandler.onInit();
        return messageHandler;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        return new MultiMqttMessageHandler();
    }
    //输出通道
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }



    //接收通道
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }


    //配置client,监听的topic
    @Bean
    public MessageProducer inbound() {
        //监听全部消息
        MyMqttPahoMessageDrivenChannelAdapter adapter = new MyMqttPahoMessageDrivenChannelAdapter(null,clientId ,
            mqttClientFactory(),
                "EV/SEND/SRV000");
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    private SettingMapper settingMapper;
    @Autowired
    private EnvironmentMapper environmentMapper;
    @Autowired
    private MqttGateway mqttGateway;


    //通过通道获取数据
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            //获取订阅消息
            try {
                log.info( message.getPayload().toString());
                JSONObject jsonObject = JSON.parseObject((String) message.getPayload());
                String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
                assert topic != null;
                String loginName = topic.substring(topic.lastIndexOf("/") + 1); //获取当前登录人

                Setting setting = settingMapper.selectById(6);

                //单聊
                if (topic.equals("EV/SEND/SRV000")){
                    System.out.println(jsonObject);
                    String placeName = jsonObject.getString("placeName");
                    QueryWrapper<Place> wrapper = new QueryWrapper<>();
                    wrapper.eq("place_name",placeName);
                    Place place = placeMapper.selectOne(wrapper);
                    if (place == null){
                        log.info("农场->"+placeName+"在系统中不存在!");
                    }else {
                        Double temValue = jsonObject.getDouble("temValue");
                        Double humValue = jsonObject.getDouble("humValue");
                        Double lightValue = jsonObject.getDouble("lightValue");
                        Environment environment = new Environment();
                        environment.setStatus(1);
                        String alarmInfo = "";

                        if (temValue > setting.getMaxTemValue()){
                            environment.setStatus(2);
                            alarmInfo = alarmInfo +"温度较高/";
                        }else if (temValue < setting.getMinTemValue()){
                            environment.setStatus(2);
                            alarmInfo = alarmInfo +"温度较低/";
                        }
                        if (humValue > setting.getMaxHumValue()){
                            environment.setStatus(2);
                            alarmInfo = alarmInfo +"湿度较高/";
                        }else if (humValue < setting.getMinHumValue()){
                            environment.setStatus(2);
                            alarmInfo = alarmInfo +"湿度较低/";
                        }
                        if (lightValue > setting.getMaxLightValue()){
                            environment.setStatus(2);
                            alarmInfo = alarmInfo +"光强较高/";
                        }else if (lightValue < setting.getMinLightValue()){
                            environment.setStatus(2);
                            alarmInfo = alarmInfo +"光强较低";
                        }
                        environment.setAlarmInfo(alarmInfo);
                        environment.setCreateTime(TimeUtil.getCurrentTime());
                        environment.setUpdateTime(TimeUtil.getCurrentTime());
                        environment.setPlaceId(place.getId());
                        environment.setTemValue(temValue);
                        environment.setHumValue(humValue);
                        environment.setLightValue(lightValue);
                        environmentMapper.insert(environment);
                        mqttGateway.sendToMqtt("BACK/SRV000/EV",JSON.toJSONString(environment));
                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        };
    }
}
