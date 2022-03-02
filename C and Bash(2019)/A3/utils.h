//.h for utils
//used in poly.h for powi, used in polyapp.c for powi and parse, and utils.c 
void parse(char *str,int *c, int *e);//parses through string and sets the arg1 and arg2 to values found in string.
int powi(int x, int e);// raises x value (arg0)  to e(exponent) and returns the sum= (x^e)
