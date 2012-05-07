
BEGIN {srand()} 

# Choosing 20% of the contents b/c we need about 1MM and there are 5MM
# queries in the input file
!/^$/ { 
	if (rand() <= .2) {
		# At least 2 characters in simulated as-you-type query
		num=int(rand() * (length($0)-2)) +  2 
		print substr($0,1,num) 
	}

}
