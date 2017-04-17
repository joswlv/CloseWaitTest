package com.test.server;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jo_seungwan on 2017. 4. 17..
 */
@Configuration
public class Config {

    @Bean
    public EmbeddedServletContainerFactory tomcatContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addAdditionalTomcatConnectors(tomcatConnector());
        return factory;
    }

    @Bean
    public Connector tomcatConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(8009);
        connector.setAttribute("connectionTimeout", -1);
        connector.setAttribute("maxConnections", -1);
        connector.setAttribute("bindOnInit", false);

        return connector;
    }

}
