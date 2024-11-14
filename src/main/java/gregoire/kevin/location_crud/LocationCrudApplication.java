package gregoire.kevin.location_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class LocationCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationCrudApplication.class, args);
	}

    @Configuration
    public class ThymeleafConfig {

        @Bean
        public ViewResolver viewResolver() {
            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
            resolver.setPrefix("/WEB-INF/templates/");
            resolver.setSuffix(".html");
            return resolver;
        }

        @Bean
        public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
            FilterRegistrationBean<HiddenHttpMethodFilter> filterRegistrationBean = new FilterRegistrationBean<>(
                    new HiddenHttpMethodFilter());
            filterRegistrationBean.setOrder(1); // Priorité du filtre
            return filterRegistrationBean;
        }
    }

}