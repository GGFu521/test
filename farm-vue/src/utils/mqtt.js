import * as mqtt from "mqtt/dist/mqtt.min";
const qosList = [0, 1, 2];
import {onMounted, reactive, ref} from "vue";
import emitter from "@/utils/eventBus";
import {formToJSON} from "axios";
import {ElNotification} from "element-plus";
const retryTimes = ref(0);
const connection = reactive({
    // ws or wss
    protocol: "ws",
    host: "127.0.0.1",
    // ws -> 8083; wss -> 8084
    port: 8083,
    clientId: "emqx_vue3_" + Math.random().toString(16).substring(2, 8),
    username: "emqx_test",
    password: "emqx_test",
    clean: true,
    connectTimeout: 30 * 1000, // ms
    reconnectPeriod: 4000, // ms
});

 let client = ref({
    connected: false,
});
 const createConnection = () => {
    try {
        const { protocol, host, port, ...options } = connection;
        const connectUrl = `${protocol}://${host}:${port}/mqtt`;

        client.value = mqtt.connect(connectUrl, options);

        if (client.value.on) {
            // https://github.com/mqttjs/MQTT.js#event-connect
            client.value.on("connect", () => {
                console.log("connection successful");
            });

            // https://github.com/mqttjs/MQTT.js#event-reconnect
            client.value.on("reconnect", handleOnReConnect);

            // https://github.com/mqttjs/MQTT.js#event-error
            client.value.on("error", (error) => {
                console.log("connection error:", error);
            });

            // https://github.com/mqttjs/MQTT.js#event-message
            client.value.on("message", (topic, message) => {
                let parse = JSON.parse(`${message}`);
                const info = {
                    receiveMessage:parse,
                    topic
                }
                emitter.emit('message', info);
                console.log(`received message: ${message} from topic: ${topic}`);
                if (parse.status === 2){
                    //报警
                    open2(parse.alarmInfo)
                }
            });
        }
    } catch (error) {
        console.log("mqtt.connect error:", error);
    }
};
const handleOnReConnect = () => {
    retryTimes.value += 1;
    if (retryTimes.value > 5) {
        try {
            client.value.end();
            console.log("connection maxReconnectTimes limit, stop retry");
        } catch (error) {
            console.log("handleOnReConnect catch error:", error);
        }
    }
};

const doSubscribe = (topic,qos) =>{
    client.value.subscribe(topic, { qos }, (error, granted) => {
            if (error) {
                console.log("subscribe error:", error);
                return;
            }
            console.log("subscribe successfully:", granted);
        }
    );
}
const doUnSubscribe = (topic,qos) => {
    client.value.unsubscribe(topic, { qos }, (error) => {
        if (error) {
            console.log("unsubscribe error:", error);
            return;
        }
        console.log(`unsubscribed topic: ${topic}`);
    });
};
const doPublish = (topic,  payload,qos) => {
    client.value.publish(topic, payload, { qos }, (error) => {
        if (error) {
            console.log("publish error:", error);
            return;
        }
        console.log(`published message: ${payload}`);
    });
};

const destroyConnection = () => {
    if (client.value.connected) {
        try {
            client.value.end(false, () => {
                console.log("disconnected successfully");
            });
        } catch (error) {
            console.log("disconnect error:", error);
        }
    }
};

export {createConnection,client,doPublish,doUnSubscribe,doSubscribe,destroyConnection}


const open2 = (message) => {
    ElNotification({
        title: '消息提醒',
        message: message,
        position: 'bottom-right',
        duration:0
    })
}