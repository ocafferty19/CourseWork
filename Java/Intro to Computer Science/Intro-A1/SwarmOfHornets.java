
public class SwarmOfHornets{
	private Hornet h[];
	private int size;
	public SwarmOfHornets(){
		this.size=0;
		this.h= new Hornet[0];
		
	}
	public int sizeOfSwarm() {
		return(this.size);
	}
	public Hornet[] getHornets(){
		return(this.h);
	}
	public Hornet getFirstHornet(){
	for(int i=0; i<this.size;i++) {
			if(this.h[i]!=null) {
				return this.h[i];
			}
	}
		if(this.size>=1)return this.h[0];
		return null;
		//if
		
	}
	public void addHornet(Hornet a) {

		int i;
		Hornet[] tmp=new Hornet[this.size+1];
		for(i=0;i<this.size;i++) {
			tmp[i]=this.h[i];
		}
		tmp[i]=a;
		this.size++;
		this.h=new Hornet[size];
		this.h=tmp;
		
		
	}
	public boolean removeHornet(Hornet r) {
		boolean a= false;
		boolean b=false;
		if(size==0) {
			return a;
		}
		Hornet[] tmp= new Hornet[this.size-1];
		for(int i=0; i<this.size; i++) {
			if((this.h[i].equals(r))&&(!b)) {
				a=true;
				b=true;
			
			} else if(a!=true) {
				tmp[i]=this.h[i];
			} else if (a==true){
			 tmp[i-1]=this.h[i];
			}
		
		}
		this.h=tmp;
		this.size=this.size-1;
		
		return a;
		
		
		
		
	}


	
}