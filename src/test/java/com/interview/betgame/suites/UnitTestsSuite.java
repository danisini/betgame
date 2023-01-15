package com.interview.betgame.suites;

import com.interview.betgame.controller.GameControllerTest;
import com.interview.betgame.mapper.GameMapperTest;
import com.interview.betgame.service.impl.GameServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GameControllerTest.class,
        GameMapperTest.class,
        GameServiceImplTest.class
})
public class UnitTestsSuite {

}
