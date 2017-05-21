def hw1(input, output):
	f = open(input, 'r')
	r = f.read()
	inp = r.splitlines()
	out = open(output, 'w')
	a = 'abcdefghijklmnopqrstuvwxyz'
	c = 0
	for i in range(len(inp)):
		s = list(inp[i])
		if len(s) < len(a):
			out.write('false\n')
		else:
			for i in range(len(a)):
				if a[i] in s:
					c = c + 1
			if c is len(a):
				out.write('true\n')
			else:
				out.write('false\n')
		c = 0
	out.close()
	f.close()
