
public class TankyBee extends HoneyBee{
	private int damage;
	private int armor;
	public TankyBee(Tile p, int d, int a) {
		super(p,30,3);
		this.damage=d;
		this.armor=a;
	}
	public boolean takeAction() {
		if(super.getPosition().getHornet()!=null) {//if hornet is on tile do damage and return true
			super.getPosition().getHornet().takeDamage(this.damage);
			return true;
		}
		return false;
			
	
		
	}
	public void takeDamage(int i) {
		double m= Math.floor(100)/(100+this.armor);
		int z=(int)Math.floor(m*i);
		super.takeDamage(z);
		
	}
	public boolean equals(Object o) {
		if(o instanceof TankyBee) {
			TankyBee t=(TankyBee) o;
			if((t.getPosition().equals(this.getPosition()))&&(this.getHealth()==t.getHealth())&&(this.damage==t.damage)&&(t.armor==this.armor)) {
				return true;
			}
		}
		return false;
	}
}