package com.pollapp.test.service;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.pollapp.configuration.MethodSecurityConfig;
import com.pollapp.test.configuration.AbstractDBTest;
import com.pollapp.test.configuration.DatabaseTesterConfig;
import com.pollapp.test.configuration.SecurityTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DatabaseTesterConfig.class,MethodSecurityConfig.class,SecurityTestConfig.class},loader=AnnotationConfigContextLoader.class)
public abstract class AbstractServiceTest extends AbstractDBTest {}
