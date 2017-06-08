package com.pollapp.configuration;
 

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pollapp.dao.UserDao;
import com.pollapp.service.ImageService;
import com.pollapp.service.UserService;
import com.pollapp.service.impl.ImageServiceImpl;
import com.pollapp.service.impl.UserServiceImpl;
import com.pollapp.util.ImageUtil;
 
@Configuration
@EnableWebMvc
@Import({TransactionConfig.class,DataSourceConfig.class,SecurityConfig.class})
@ComponentScan(basePackages = "com.pollapp",excludeFilters= {@ComponentScan.Filter(type= FilterType.REGEX,pattern="com.pollapp.test.*")})
public class AppConfig extends WebMvcConfigurerAdapter {

    private static final String DEFAULT_IMAGE_FOLDER = "/src/images";
    
	@Value("${image.placeholder}")
	private String placeholderFileName;
	
	@Value("${image.folder}")
	private String imageFolder;
	
	@Value("${image.convertJpg}")
	private boolean convertToJpg;
	
	@Value("${image.maxUncompressed}")
	private long maxUncompressedSize;
	
	@Value("${image.placeholder.filename}")
	private String placeholder;
	
	@Value("${image.defaultProfileImagePath}")
	private String defaultProfilePic;
	
	@Bean
	@Autowired
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
    
	@Bean
	public ViewResolver viewResolver(){
		return new InternalResourceViewResolver("/WEB-INF/", ".jsp");
	}
	
	@Bean
	public ImageService imageServiceImpl() throws IOException{
		return new ImageServiceImpl(imageUtil(), placeholderFileName);
	}
	
	@Bean
	public ImageUtil imageUtil() throws IOException{
        if(imageFolder.isEmpty()){// Revert to folder in src
            imageFolder = System.getProperty("user.dir").concat(DEFAULT_IMAGE_FOLDER);
            if(!new File(imageFolder).exists()){
                throw new IOException("Misconfiguration: Cannot access imagefolder - images won't load ");
            }

        }	    
		return new ImageUtil(imageFolder, convertToJpg, maxUncompressedSize);
	}
	
    @Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}	 

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
    
    @Bean
    @Autowired
    public UserService userServiceImpl(UserDao dao,MutableAclService service, PasswordEncoder encoder){
        return new UserServiceImpl(dao, service, encoder, defaultProfilePic);
    }
    
    
}