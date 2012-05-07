# distill.awk - extract only queries which generated clickthroughs for 
# indexing.  Collapse consecutive duplicate queries (mostly same user).
BEGIN { FS = "\t" }

# Ignore the header row
NR > 1 && $5 != "" && a !~ $2 { 
	print $2; 
	a=$2;
}


