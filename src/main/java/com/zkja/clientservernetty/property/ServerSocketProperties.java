package com.zkja.clientservernetty.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author  zzr
 */

@Component
@ConfigurationProperties(prefix = "serverSocket")
@PropertySource("classpath:socket.properties")
public class ServerSocketProperties {

    /**
     * 端口
     */
    private String port;
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private String smcUrl;
    private String serverport;

    public String getServerport() {
        return serverport;
    }

    public void setServerport(String serverport) {
        this.serverport = serverport;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

	public String getSmcUrl() {
		return smcUrl;
	}

	public void setSmcUrl(String smcUrl) {
		this.smcUrl = smcUrl;
	}

	
    
}
