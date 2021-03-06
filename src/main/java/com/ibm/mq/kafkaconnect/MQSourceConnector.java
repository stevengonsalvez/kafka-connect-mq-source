/**
 * Copyright 2017 IBM Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ibm.mq.kafkaconnect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Width;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQSourceConnector extends SourceConnector {
    private static final Logger log = LoggerFactory.getLogger(MQSourceConnector.class);

    public static final String CONFIG_GROUP_MQ = "mq";

    public static final String CONFIG_NAME_MQ_QUEUE_MANAGER = "mq.queue.manager";
    public static final String CONFIG_DOCUMENTATION_MQ_QUEUE_MANAGER = "The name of the MQ queue manager.";
    public static final String CONFIG_DISPLAY_MQ_QUEUE_MANAGER = "Queue manager";

    public static final String CONFIG_NAME_MQ_CONNECTION_NAME_LIST = "mq.connection.name.list";
    public static final String CONFIG_DOCUMENTATION_MQ_CONNNECTION_NAME_LIST = "A list of one or more host(port) entries for connecting to the queue manager. Entries are separated with a comma.";
    public static final String CONFIG_DISPLAY_MQ_CONNECTION_NAME_LIST = "List of connection names for queue manager";

    public static final String CONFIG_NAME_MQ_CHANNEL_NAME = "mq.channel.name";
    public static final String CONFIG_DOCUMENTATION_MQ_CHANNEL_NAME = "The name of the server-connection channel.";
    public static final String CONFIG_DISPLAY_MQ_CHANNEL_NAME = "Channel name";

    public static final String CONFIG_NAME_MQ_QUEUE = "mq.queue";
    public static final String CONFIG_DOCUMENTATION_MQ_QUEUE = "The name of the source MQ queue.";
    public static final String CONFIG_DISPLAY_MQ_QUEUE = "Source queue";

    public static final String CONFIG_NAME_MQ_USER_NAME = "mq.user.name";
    public static final String CONFIG_DOCUMENTATION_MQ_USER_NAME = "The user name for authenticating with the queue manager.";
    public static final String CONFIG_DISPLAY_MQ_USER_NAME = "User name";

    public static final String CONFIG_NAME_MQ_PASSWORD = "mq.password"; 
    public static final String CONFIG_DOCUMENTATION_MQ_PASSWORD = "The password for authenticating with the queue manager.";
    public static final String CONFIG_DISPLAY_MQ_PASSWORD = "Password";

    public static final String CONFIG_NAME_MQ_MESSAGE_BODY_JMS = "mq.message.body.jms"; 
    public static final String CONFIG_DOCUMENTATION_MQ_MESSAGE_BODY_JMS = "Whether to interpret the message body as a JMS message type.";
    public static final String CONFIG_DISPLAY_MQ_MESSAGE_BODY_JMS = "Message body as JMS";

    public static final String CONFIG_NAME_TOPIC = "topic"; 
    public static final String CONFIG_DOCUMENTATION_TOPIC = "The name of the target Kafka topic.";
    public static final String CONFIG_DISPLAY_TOPIC = "Target Kafka topic";

    public static String VERSION = "0.1";

    private Map<String, String> configProps;

    /**
     * Get the version of this connector.
     *
     * @return the version, formatted as a String
     */
    @Override public String version() {
        return VERSION;
    }

    /**
     * Start this Connector. This method will only be called on a clean Connector, i.e. it has
     * either just been instantiated and initialized or {@link #stop()} has been invoked.
     *
     * @param props configuration settings
     */
    @Override public void start(Map<String, String> props) {
        configProps = props;
        for (final Entry<String, String> entry: props.entrySet()) {
            log.trace("Connector props entry {} : {}", entry.getKey(), entry.getValue());
        }
    }

    /**
     * Returns the Task implementation for this Connector.
     */
    @Override public Class<? extends Task> taskClass() {
        return MQSourceTask.class;
    }   

    /**
     * Returns a set of configurations for Tasks based on the current configuration,
     * producing at most count configurations.
     *
     * @param maxTasks maximum number of configurations to generate
     * @return configurations for Tasks
     */
    @Override public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> taskConfigs = new ArrayList<>();
        for (int i = 0; i < maxTasks; i++)
        {
            taskConfigs.add(configProps);
        }
        return taskConfigs;
    }

    /**
     * Stop this connector.
     */
    @Override public void stop() {

    }

    /**
     * Define the configuration for the connector.
     * @return The ConfigDef for this connector.
     */
    @Override public ConfigDef config() {
        ConfigDef config = new ConfigDef();

        config.define(CONFIG_NAME_MQ_QUEUE_MANAGER, Type.STRING, ConfigDef.NO_DEFAULT_VALUE, Importance.HIGH,
                      CONFIG_DOCUMENTATION_MQ_QUEUE_MANAGER, CONFIG_GROUP_MQ, 1, Width.MEDIUM,
                      CONFIG_DISPLAY_MQ_QUEUE_MANAGER);

        config.define(CONFIG_NAME_MQ_CONNECTION_NAME_LIST, Type.STRING, ConfigDef.NO_DEFAULT_VALUE, Importance.HIGH,
                      CONFIG_DOCUMENTATION_MQ_CONNNECTION_NAME_LIST, CONFIG_GROUP_MQ, 2, Width.LONG,
                      CONFIG_DISPLAY_MQ_CONNECTION_NAME_LIST);

        config.define(CONFIG_NAME_MQ_CHANNEL_NAME, Type.STRING, ConfigDef.NO_DEFAULT_VALUE, Importance.HIGH,
                      CONFIG_DOCUMENTATION_MQ_CHANNEL_NAME, CONFIG_GROUP_MQ, 3, Width.MEDIUM,
                      CONFIG_DISPLAY_MQ_CHANNEL_NAME);

        config.define(CONFIG_NAME_MQ_QUEUE, Type.STRING, ConfigDef.NO_DEFAULT_VALUE, Importance.HIGH,
                      CONFIG_DOCUMENTATION_MQ_QUEUE, CONFIG_GROUP_MQ, 4, Width.LONG,
                      CONFIG_DISPLAY_MQ_QUEUE);

        config.define(CONFIG_NAME_MQ_USER_NAME, Type.STRING, null, Importance.MEDIUM,
                      CONFIG_DOCUMENTATION_MQ_USER_NAME, CONFIG_GROUP_MQ, 5, Width.MEDIUM,
                      CONFIG_DISPLAY_MQ_USER_NAME);

        config.define(CONFIG_NAME_MQ_PASSWORD, Type.PASSWORD, null, Importance.MEDIUM,
                      CONFIG_DOCUMENTATION_MQ_PASSWORD, CONFIG_GROUP_MQ, 6, Width.MEDIUM,
                      CONFIG_DISPLAY_MQ_PASSWORD);

        config.define(CONFIG_NAME_MQ_MESSAGE_BODY_JMS, Type.BOOLEAN, Boolean.FALSE, Importance.MEDIUM,
                      CONFIG_DOCUMENTATION_MQ_MESSAGE_BODY_JMS, CONFIG_GROUP_MQ, 7, Width.SHORT,
                      CONFIG_DISPLAY_MQ_MESSAGE_BODY_JMS);

        config.define(CONFIG_NAME_TOPIC, Type.STRING, null, Importance.HIGH,
                      CONFIG_DOCUMENTATION_TOPIC, null, 0, Width.MEDIUM,
                      CONFIG_DISPLAY_TOPIC);

        return config;
    }
}