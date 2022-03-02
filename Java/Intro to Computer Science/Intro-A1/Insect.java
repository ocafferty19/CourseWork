
public abstract class Insect{
	private Tile pos;
	private int hitPoints;
	
	public Insect(Tile p, int hp){
		this.pos=p;
		this.hitPoints=hp;
		if(this instanceof HoneyBee && this.pos.getBee() != null) {
			
				throw new IllegalArgumentException("stop");
			}else {
				//System.out.println(this instanceof BusyBee);
				this.pos.addInsect(this);
			}
		
		
		
		
	}
	public final Tile getPosition() {
		return this.pos;
	}
	public final int getHealth() {
		return(this.hitPoints);
	}
	public void setPosition(Tile p) {
		this.pos=p;
	}
	public void takeDamage(int d) {
			int f=d;
			if(this instanceof HoneyBee) {
				if(this.pos.isHive()) {
					
					f=(int) Math.floor(d*.9);
				} 
			}
			this.hitPoints=this.hitPoints-f;
			
		
		if(this.hitPoints<=0) {
			this.pos.removeInsect(this);
		}
		
	}
	public abstract boolean takeAction();
	public boolean equals(Object o) {
		if(o instanceof Insect){
			Insect p=(Insect) o;
			if((p.hitPoints==this.hitPoints)&&(this.pos==p.pos)) {
				
			return true;
			}
		}
		return false;
	}
	
	
	
}