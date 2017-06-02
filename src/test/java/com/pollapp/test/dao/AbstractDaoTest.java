package com.pollapp.test.dao;



import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.pollapp.test.configuration.AbstractDBTest;
import com.pollapp.test.configuration.DaoTestConfig;
import com.pollapp.test.configuration.DatabaseTesterConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DatabaseTesterConfig.class,DaoTestConfig.class},loader=AnnotationConfigContextLoader.class)
public abstract class AbstractDaoTest extends AbstractDBTest{}
