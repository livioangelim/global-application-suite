package config;

import convertor.ReservationEntityToReservationResponseConverter;
import convertor.ReservationRequestToReservationEntityConverter;
import convertor.RoomEntityToReservableRoomResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {

        @Autowired
        ApplicationContext context;

        private Set<Converter<?, ?>> getConverters() { // Parameterized Converter
                Set<Converter<?, ?>> converters = new HashSet<>(); // Parameterized Converter & diamond operator
                converters.add(new RoomEntityToReservableRoomResponseConverter());
                converters.add(new ReservationRequestToReservationEntityConverter(context));
                converters.add(new ReservationEntityToReservationResponseConverter());

                return converters;
        }

        @Bean
        public ConversionService conversionService() {
                ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
                bean.setConverters(getConverters());
                bean.afterPropertiesSet();

                return bean.getObject();
        }
}
