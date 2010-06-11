import SocketServer, BaseHTTPServer, SimpleHTTPServer, thread, socket, os, sys

"""
A simple modification of Tuulos' 50k test that will serve up strings on
an ad-hoc http server.  This is primarily used for simple tests.
"""
class Server(SocketServer.ThreadingMixIn, BaseHTTPServer.HTTPServer):
	allow_reuse_address = True

class Handler(SimpleHTTPServer.SimpleHTTPRequestHandler):
	def do_GET(self):
		d = DATA_GEN.next()
		self.send_response(200)
		self.send_header("Content-length", len(d))
		self.end_headers()
		self.wfile.write(d)

def makeurl(inputs):
		host = "http://%s:%d" % (socket.gethostname(), PORT)
		return ["%s/%s" % (host, i) for i in inputs]

#	configStrings: string array of configurations
#	port: the local port on which to launch the ad-hoc http server
def run_server(configStrings, port):
		print 'HTTP set to port %s, serving up %s files' % (str(port), str(len(configStrings)))

		global DATA_GEN 
		DATA_GEN = conf_gen(configStrings)

		global DATA_LEN
		len(configStrings)

		global PORT
		PORT = port

		httpd = Server(('', PORT), Handler)
		thread.start_new_thread(httpd.serve_forever, ())

def conf_gen(configs):
	for config in configs:
		yield config
