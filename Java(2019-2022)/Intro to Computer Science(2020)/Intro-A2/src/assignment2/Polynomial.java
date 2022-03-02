package assignment2;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.ListIterator;

public class Polynomial implements DeepClone<Polynomial> {
	private SLinkedList<Term> polynomial;

	public int size() {
		return polynomial.size();
	}

	private Polynomial(SLinkedList<Term> p) {
		polynomial = p;
	}

	public Polynomial() {
		polynomial = new SLinkedList<Term>();
	}

	// Returns a deep copy of the object.
	public Polynomial deepClone() {
		return new Polynomial(polynomial.deepClone());
	}

	/*
	 * TODO: Add new term to the polynomial. Also ensure the polynomial is in
	 * decreasing order of exponent.
	 */
	public void addTerm(Term t) {
		boolean tmp = false;
		boolean p = false;
		int i = 0;
		int c = -1;
		if (polynomial.size() == 0) {
			this.polynomial.addFirst(t);
			tmp = true;
		}
		for (Term currentTerm : polynomial) {
			if (t.getExponent() == currentTerm.getExponent() && !tmp) {
				currentTerm.setCoefficient(currentTerm.getCoefficient().add(t.getCoefficient()));
				tmp = true;
			} else if (t.getExponent() > currentTerm.getExponent() && !tmp) {
				c = i;
				tmp = true;
			}
			i++;
		}
		if (c != -1) {
			this.polynomial.add(c, t);
		} else if (c == -1 && !tmp) {
			this.polynomial.addLast(t);
		}

		// The for loop iterates over each term in the polynomial!!
		// Example: System.out.println(currentTerm.getExponent()) should print the
		// exponents of each term in the polynomial when it is not empty.
	}

	/**** ADD CODE HERE ****/

	// Hint: Notice that the function SLinkedList.get(index) method is O(n),
	// so if this method were to call the get(index)
	// method n times then the method would be O(n^2).
	// Instead, use a Java enhanced for loop to iterate through
	// the terms of an SLinkedList.
	/*
	 * for (Term currentTerm: polynomial) { // The for loop iterates over each term
	 * in the polynomial!! // Example: System.out.println(currentTerm.getExponent())
	 * should print the exponents of each term in the polynomial when it is not
	 * empty. }
	 */

	public Term getTerm(int index) {
		return polynomial.get(index);
	}

	// TODO: Add two polynomial without modifying either
	public static Polynomial add(Polynomial p1, Polynomial p2) {
		Polynomial combine = new Polynomial();
		Iterator<Term> it1 = p1.polynomial.iterator();
		Iterator<Term> it2 = p2.polynomial.iterator();
		Term tmp1;
		Term tmp2;
		boolean end1 = false;
		boolean end2 = false;
		if (p1.size() == 0) {
			for (Term t : p2.polynomial) {
				combine.addTermLast(t.deepClone());
			}
			return combine;
		} else {
			tmp1 = it1.next();

		}
		if (p2.size() == 0) {
			for (Term t : p1.polynomial) {
				combine.addTermLast(t.deepClone());
			}
			return combine;
		} else {
			tmp2 = it2.next();

		}
		for (int i = 0; i < p1.size() + p2.size(); i++) {

			if (end1 && end2) {
				break;
			} else if (tmp1.getExponent() == tmp2.getExponent() && (!end1 || !end2)) {
				BigInteger ad = (tmp1.getCoefficient().add(tmp2.getCoefficient()));
				Term addd = new Term(tmp1.getExponent(), ad);
				combine.addTermLast(addd.deepClone());
				if (it1.hasNext()) {
					tmp1 = it1.next();
				} else {
					tmp1.setExponent(-1);
					end1 = true;
				}
				if (it2.hasNext()) {
					tmp2 = it2.next();
				} else {
					tmp2.setExponent(-1);
					end2 = true;
				}
			} else if (tmp1.getExponent() > tmp2.getExponent() && !end1) {
				combine.addTermLast(tmp1.deepClone());
				if (it1.hasNext()) {
					tmp1 = it1.next();
				} else {
					tmp1.setExponent(-1);
					end1 = true;
				}

			} else if (!end2) {
				combine.addTermLast(tmp2.deepClone());
				if (it2.hasNext()) {
					tmp2 = it2.next();
				} else {
					tmp2.setExponent(-1);
					end2 = true;
				}
			}
		}

		// System.out.println(combine.toString());
		return combine;
	}

	// TODO: multiply this polynomial by a given term.
	public void multiplyTerm(Term t) {
		int j = t.getExponent();

		for (Term currentTerm : polynomial) {

			int a = currentTerm.getExponent();

			currentTerm.setExponent(a + j);
			currentTerm.setCoefficient(currentTerm.getCoefficient().multiply(t.getCoefficient()));
		}

		/**** ADD CODE HERE ****/
	}

	// TODO: multiply two polynomials
	public static Polynomial multiply(Polynomial p1, Polynomial p2) {
		Polynomial combine = new Polynomial();
		Iterator<Term> it1 = p1.polynomial.iterator();
		// Iterator<Term> it2= p2.polynomial.iterator();
		if (p1 == null || p2 == null) {
			return combine;
		}
		int[] exp = new int[p1.size() * p2.size()];
		BigInteger[] co = new BigInteger[p1.size() * p2.size()];
		Term one;
		int count = 0;
		while (it1.hasNext()) {
			one = it1.next();
			Iterator<Term> it2 = p2.polynomial.iterator();
			while (it2.hasNext()) {
				Term two = it2.next();
				exp[count] = one.getExponent() + two.getExponent();
				co[count] = one.getCoefficient().multiply(two.getCoefficient());
				combine.addTermLast(new Term(exp[count], co[count]));
				count++;
			}

		}

		return combine;
	}

//        if(p1.size()==0||p2.size()==0){
//            return combine;
//        }
//
//
//
//        int[] exp=new int[p1.size()*p2.size()];
//        BigInteger[] co= new BigInteger[p1.size()* p2.size()];
//        int count=0;
//        for(int i=0; it1.hasNext();i++){
//            Term one= it1.next();
//            Iterator<Term> it2= p2.polynomial.iterator();
//            for(int j=0; it2.hasNext();j++){
//
//                Term two= it2.next();
//                Term tmp=new Term(one.getExponent()+ two.getExponent(),one.getCoefficient().multiply(two.getCoefficient()));
//                exp[count]= tmp.getExponent();
//                co[count]=tmp.getCoefficient();
//                count++;
//            }

//
//        Polynomial c=new Polynomial();
//        int size= p1.size()+p2.size()-1;
//
//        for(Term o: p1.polynomial){
//            for(Term t: p2.polynomial){
//                Term tmp=new Term(o.getExponent()+t.getExponent(), o.getCoefficient().multiply(t.getCoefficient()));
//                c.addTermLast(tmp);
//            }
//        }
//
//        /**** ADD CODE HERE ****/
//

//    private static Polynomial removeDuplicates(int[] a, BigInteger[] b){
//        Polynomial s= new Polynomial();
//        int i=0;
//        int j=0;
//        while(i<a.length){
//            int tExp= a[i];
//            while(j<a.length){
//                int jExp=a[j];
//                if(a[i]==a[j]){
//
//                    a[j]=0;
//                    b[j]=BigInteger.valueOf(0);
//                } else {
//                    j++;
//                }
//            }
//            i++;
//        }
//        return s;
//    }

	// TODO: evaluate this polynomial.
	// Hint: The time complexity of eval() must be order O(m),
	// where m is the largest degree of the polynomial. Notice
	// that the function SLinkedList.get(index) method is O(m),
	// so if your eval() method were to call the get(index)
	// method m times then your eval method would be O(m^2).
	// Instead, use a Java enhanced for loop to iterate through
	// the terms of an SLinkedList.

	public BigInteger eval(BigInteger x) {
		BigInteger sum = new BigInteger("0");
		int dif;
		boolean hasZTerm = false;
		int n = 0;
		for (Term t : polynomial) {
			int i = t.getExponent();
			dif = n - i;
			if (dif > 0) {
				// for (int j = 1; j <= dif; j++) {

				sum = sum.multiply(x.pow(dif));
				// System.out.println(sum.toString());
				// }
			}
			n = t.getExponent();
			sum = sum.add(t.getCoefficient());

		}
		dif = n - 0;
		if (dif != 0) {
			sum = sum.multiply(x.pow(dif));
		}
		return sum;
	}

	// Checks if this polynomial is a clone of the input polynomial
	public boolean isDeepClone(Polynomial p) {
		if (p == null || polynomial == null || p.polynomial == null || this.size() != p.size())
			return false;

		int index = 0;
		for (Term term0 : polynomial) {
			Term term1 = p.getTerm(index);

			// making sure that p is a deep clone of this
			if (term0.getExponent() != term1.getExponent()
					|| term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
				return false;

			index++;
		}
		return true;
	}

	// This method blindly adds a term to the end of LinkedList polynomial.
	// Avoid using this method in your implementation as it is only used for
	// testing.
	public void addTermLast(Term t) {
		polynomial.addLast(t);
	}

	@Override
	public String toString() {
		if (polynomial.size() == 0)
			return "0";
		return polynomial.toString();
	}
}
