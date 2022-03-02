
public abstract class HoneyBee extends Insect{
	private int fCost;
	public HoneyBee(Tile p, int hp, int cost) {
		super(p,hp);
		this.fCost= cost;
		
	}
	public int getCost() {
		return(this.fCost);
	}
	
	
	public boolean equals(Object o) {
		
		if(o instanceof HoneyBee) {
			HoneyBee q=(HoneyBee)o;
			if((q.getCost()==this.getCost())&&(this.getPosition().equals(q.getPosition()))&&(this.fCost==q.fCost)&&(this.getHealth()==q.getHealth())){
				//if() {
					return true;
				//}
			}
		}
		return false;
	}
}