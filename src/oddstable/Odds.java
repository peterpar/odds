package oddstable;


public class Odds implements Comparable<Odds>   {
	
	enum RoundingType {
		ForDefender,
		TrueRounding,
		SCSRounding
	};
	
	enum Special {
		BELOW,
		OVER,
		OK
	};
	
	int fm;
	int to;
	double decimal;
	Special valueType;
	
	public Odds( int fm, int to ) {
		this.fm = fm ;
		this.to = to;
		decimal = (double)fm/(double)to;
		valueType = Special.OK;
	}

	@Override
	public String toString() {
		if( valueType == Special.OK )
			return ""+fm+"-"+to;
		
		return valueType.name();
	}
	
	public String toDebugString() {
		return  toString() + "(" + decimal + ")"; 
		
	}
	
	public Odds( String fm, String to ) {
		this( Integer.parseInt( fm ), Integer.parseInt(to));
	}

	public Odds( Special sv ) {
		valueType = sv ;
	}
	
	@Override
	public int compareTo(Odds o) {
		if( valueType == Special.OVER && o.valueType != Special.OVER )
			return 1 ;
		
		if( valueType == Special.BELOW && o.valueType != Special.BELOW )
			return -1 ;
		
		if( valueType == o.valueType && valueType != Special.OK )
			return 0;
			
		if( this.decimal > o.decimal )
			return 1 ;
		else
		if( this.decimal < o.decimal )
			return -1;
		else
			return 0;
	}
}
