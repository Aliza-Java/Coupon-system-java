package jbcourse.couponSystemPhase3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * Allowing CORS configuration - this seems to be right only for jersey?
 * 
 * package jbcourse.couponSystemPhase3;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.web.cors.CorsConfiguration; import
 * org.springframework.web.cors.UrlBasedCorsConfigurationSource; import
 * org.springframework.web.filter.CorsFilter;
 * 
 * //A helper method to overcome CORS issues from client
 * 
 * @Configuration public class RestConfig {
 * 
 * @Bean public CorsFilter corsFilter() { UrlBasedCorsConfigurationSource source
 * = new UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
 * CorsConfiguration(); config.setAllowCredentials(true);
 * config.addAllowedOrigin("*"); config.addAllowedHeader("*");
 * config.addAllowedMethod("OPTIONS"); config.addAllowedMethod("GET");
 * config.addAllowedMethod("POST"); config.addAllowedMethod("PUT");
 * config.addAllowedMethod("DELETE"); source.registerCorsConfiguration("/**",
 * config); return new CorsFilter(source); } }
 */

@SuppressWarnings("deprecation")
@Configuration
public class RestConfig{
	
	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurerAdapter() {
			public void addCorsMappings(CorsRegistry registry) {
				registry
				.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE", "TRACE")
				.allowCredentials(true);
			}
		};
	}
	
}