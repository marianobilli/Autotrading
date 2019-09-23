package autotrading.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CotizacionTesting.class,
			   InversionTesting.class,
			   EmpresaTest.class,
			   CotizacionesTest.class,
			   MovingAverageTest.class})

public class RunTestSuite {
}
