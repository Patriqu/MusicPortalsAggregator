package com.siriusbase.MusicPortalsAggregator;

import com.siriusbase.MusicPortalsAggregator.config.PrivateConfig;
import com.siriusbase.MusicPortalsAggregator.config.PublicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({PrivateConfig.class, PublicConfig.class})
public class MusicPortalsAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicPortalsAggregatorApplication.class, args);
	}

}
