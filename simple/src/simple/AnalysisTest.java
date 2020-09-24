package simple;

import org.junit.jupiter.api.Test;

class AnalysisTest {
	@Test
	public void test1() {//第一次测试
	
		String[] array= {"test/ta1.txt","test/tb1.txt","test/result1.txt"};
	    Analysis.main(array);
		
		}
	@Test
	public void test2() {//第二次测试
		
		String[] array= {"test/ta2.txt","test/tb2.txt","test/result2.txt"};
	    Analysis.main(array);	
		
	}
	@Test
	public void test3() {//第三次测试
		
		String[] array= {"test/ta3.txt","test/tb3.txt","test/result3.txt"};
	    Analysis.main(array);	
	}
	@Test
	public void test4() {//第四次测试
		
		String[] array= {"test/ta4.txt","test/tb4.txt","test/result4.txt"};
	    Analysis.main(array);
	}
	

}
