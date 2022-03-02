
public class StingyBee extends HoneyBee{
	private int damage;
	public StingyBee(Tile p,int d) {
		super(p,10,1);
		this.damage=d;
	}
	public boolean takeAction() {
		if(!this.getPosition().isOnThePath()) {
			
			return false;
		}
		Tile a=super.getPosition();
		if(this.getPosition().getHornet()!=null) {
			this.getPosition().getHornet().takeDamage(this.damage);
			return true;
		}
		for(int i=0; this.getPosition().towardTheHive()!=null;i++) {
			
			this.setPosition(this.getPosition().towardTheHive());
			if(this.getPosition().getHornet()!=null) {
				this.getPosition().getHornet().takeDamage(this.damage);
				return true;
			}
			
		}
		return false;
	}
	public boolean equals(Object o) {
		if(o instanceof StingyBee) {
			StingyBee a= (StingyBee) o;
			if((a.getPosition().equals(this.getPosition()))&&(this.getCost()==a.getCost())&&(this.damage==a.damage)) {
				return true;
			}
		}
		return false;
	}
	
}