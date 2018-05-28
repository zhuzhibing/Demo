package net.luculent.automatioin.laks.platform.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import net.luculent.automatioin.laks.platform.interceptor.AccessVerifyInterceptor;
import net.luculent.automatioin.laks.platform.interceptor.TokenVerifyInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author: zhuzb
 * @Description: 返回结果JSON解析配置
 * @Date Create In 9:02 2018/2/1
 * @Modified By:
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

//    @Bean
//    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        /*
//         * 返回的JSON字符串中含有我们并不需要的字段，那么当对应的实体类中不含有该字段时，会抛出一个异常，告诉你有些字段没有在实体类中找到
//         */
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
//
//        // 允许单引号
//        //objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        // 字段和值都加引号
//        //objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        // 数字也加引号
//        //objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
//        //objectMapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
//
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        objectMapper.registerModule(javaTimeModule);
//
//        //Null值输出空字符串
//        serializerProvider.setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
//                    IOException {
//                jsonGenerator.writeString("");
//            }
//
//        });
//
//        jsonConverter.setObjectMapper(objectMapper);
//        return jsonConverter;
//    }

//    @Bean
//    @Primary
//    @ConditionalOnMissingBean(ObjectMapper.class)
//    public ObjectMapper jsonObjectMapper() {
//
//        ObjectMapper om = new ObjectMapper();
//
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        om.registerModule(javaTimeModule);
//
//        /*
//         * 返回的JSON字符串中含有我们并不需要的字段，那么当对应的实体类中不含有该字段时，会抛出一个异常，告诉你有些字段没有在实体类中找到
//         */
//        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        // 允许单引号
//        //om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        // 字段和值都加引号
//        //om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        // 数字也加引号
//        //om.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
//        //om.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
//
//        //Null值输出空字符串
//        SerializerProvider serializerProvider = om.getSerializerProvider();
//        serializerProvider.setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString("");
//            }
//
//        });
//
//        return om;
//    }

    @Autowired
    private TokenVerifyInterceptor tokenVerifyInterceptor;

    @Autowired
    public AccessVerifyInterceptor accessVerifyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(tokenVerifyInterceptor).excludePathPatterns("/**/public/**");
        registry.addInterceptor(accessVerifyInterceptor).excludePathPatterns("/**/public/**");
        super.addInterceptors(registry);
    }


    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        // 在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);

    }

//    @Bean(name="conversionService")
//    public ConversionServiceFactoryBean getConversionService() {
//        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
//        Set<Converter> converters = new HashSet<>();
//        converters.add(new StringToJSONObjectConverter());
//        bean.setConverters(converters);
//        return bean;
//    }
//
//
//    static class StringToJSONObjectConverter implements Converter<String, JSONObject> {
//
//        StringToJSONObjectConverter() {
//        }
//
//        public JSONObject convert(String source) {
//            if (StringUtils.isNotEmpty(source)) {
//                return JSONObject.parseObject(source);
//            } else {
//                return null;
//            }
//        }
//
//    }

    /**
     * 设置允许跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "DELETE", "PUT")
//                .maxAge(3600);

    }

}



