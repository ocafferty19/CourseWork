public class Hornet extends Insect{
	private int damage;
	public Hornet(Tile p, int hp, int d){
		super(p,hp);
		this.damage=d;		
		
	}
	public boolean takeAction() {
		if(this.getPosition().getBee() != null) {
			//System.out.println(this.getPosition().getBee().getHealth());
			this.getPosition().getBee().takeDamage(this.damage);
			//System.out.println(this.getPosition().getBee().getHealth());
			return true;
		} else if(this.getPosition().isNest()) {// if nest
			
			return true;	
			
		} else if(this.getPosition().getBee() == null){ //move all hornets one tile
			if(super.getPosition().towardTheHive()!=null&&super.getPosition().towardTheHive().towardTheHive()!=null) {
				super.getPosition().createPath(super.getPosition().towardTheHive().towardTheHive(),super.getPosition());
				super.setPosition(super.getPosition().towardTheHive());
				return true;
			}
			
		
		}
		return false;
		
	}
	
	public boolean equals(Object h) {
		if(h instanceof Hornet) {
			Hornet i=(Hornet) h;
			if(super.getHealth()==super.getHealth() && this.damage==i.damage){ 
				if(super.getPosition()==super.getPosition()) {//add the i. and this.key words for this and line 22
					return true;
				}
			}
		}
		return false;
	}
}