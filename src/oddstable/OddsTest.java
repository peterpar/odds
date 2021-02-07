package oddstable;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import oddstable.Odds.RoundingType;

public class OddsTest {
	
	PossibleOddsList pol = null;
	ADCrossTab ct = null ;
	
	@Before
	public void setUp() throws Exception {
		pol = new PossibleOddsList("1-2,1-1,2-1,3-1,4-1");
		ct = new ADCrossTab(pol, 10, 10, RoundingType.SCSRounding );
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test5to2() {
		ct.convertOne(1, 2);
		ct.convertOne(5, 2);
	}

	@Test
	public void testAllOdds() {
		for( int af = 1 ; af <= 40 ; af++ )
		{
			for( int df = 1 ; df <= 20 ; df++ ) {
				
				int sml = Math.min(af, df);
				
				int fm = (int)(0.50 + (double)af/(double)sml);
				int to = (int)(0.50 + (double)df/(double)sml);

				String cods = fm + "-" + to ;
				String odds = ct.convertOne( af, df);
				
				if( odds.equals("OVER") || odds.equals("BELOW") )
					continue;
				
				if(cods.compareTo(odds) != 0 )
				{
					System.out.println("cods=" + cods + " odds=" + odds + " af=" + af + " df=" + df );
				}
				//Assert.assertEquals(cods, odds);
			}
		}
	}
	
}
