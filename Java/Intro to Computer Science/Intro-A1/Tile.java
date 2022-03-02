
public class Tile{
	private int food;
	private boolean hiveBuilt;
	private boolean hornBuilt;
	private boolean path;
	private Tile tBHive;
	private Tile tHNest;
	private HoneyBee b;
	private SwarmOfHornets p;
	public Tile() {
		//this=New Tile;
		this.hiveBuilt=false;
		this.hornBuilt=false;
		this.path=false;
		this.food=0;
		this.b=null;
		this.p= new SwarmOfHornets();
	}
	public Tile(int f, boolean hB, boolean hoB, boolean p, Tile tBH, Tile tHN, HoneyBee h, SwarmOfHornets s) {
		this.food=f;
		this.hiveBuilt=hB;
		this.hornBuilt=hoB;
		this.path=p;
		this.tBHive=tBH;
		this.tHNest=tHN;
		this.b=h;
		this.p=s;
		
	}
	public boolean isHive() {
		if(this.hiveBuilt) return true;
		
		return false;
		
	}
	public boolean isNest() {
		if(this.hornBuilt) {
			return true;
		}
		return false;
		
	}
	public void buildHive() {
		this.hiveBuilt=true;
	}
	public void buildNest() {
		this.hornBuilt=true;
		
	}
	public boolean isOnThePath() {
		if(this.path) return true; 
		
		return false;
	}
	public Tile towardTheHive() {
		if(this.path=true) {
			return this.tBHive;
		}
		return null;
	}
	public Tile towardTheNest() {
		if(this.path=true) {
			return this.tHNest;
		}
		return null;
	}
	public void createPath(Tile h, Tile n) {
		this.tBHive=h;
		this.tHNest=n;
		this.path=true;
	}
	public int collectFood() {
		int a=this.food;
		this.food=0;
		return(a);
	}
	public void storeFood(int n) {
		this.food+=n;
	}
	public HoneyBee getBee() {
		return this.b;
	}
	public Hornet getHornet() {
		return(this.p.getFirstHornet());
	}
	public int getNumOfHornets() {
		return(this.p.sizeOfSwarm());
	}
	public boolean addInsect(Insect i) {
		if(i instanceof HoneyBee) {
			if(this.b==null && this.hornBuilt==false) {
				this.b=(HoneyBee)i;
				return true;
			}
		} else if((i instanceof Hornet)){
			if((this.isHive())||(this.isNest())||(this.isOnThePath())) {
				this.p.addHornet((Hornet) i);
				return true;
			}
		}
		return false;
		
	}
	public boolean removeInsect(Insect r) {
		if(r instanceof Hornet) {
			Hornet b=(Hornet) r;
			int s=this.p.sizeOfSwarm();
			this.p.removeHornet(b);
		
			
			if(s!=this.p.sizeOfSwarm()) {
				return true;
			}
		}
		if(r instanceof HoneyBee){
			this.b=null;
			return true;
			
		}
		return false;
		
	}
}