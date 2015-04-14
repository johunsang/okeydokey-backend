
“Okeydokey-backend” is a rest method java framework. It is suitable for small companies or for company environment where the business logic is not complicated or for training. 

1.Non-blocking I/O, Asynchronous

As it is constructed based on the Servlet 3.1 of j2ee 7.0, it uses the asynchronous process method which uses the Non-blocking I/O technique.

2.Perfectly separated in to tiers

Accordingly, it possesses a strong performance advantage over the traditional I/O method. It provides development environment which is perfectly separated in to tiers, and therefore the developer can develop simply the biz class which is a business level unit class and distribute it.

3.Message format

Fundamentally, the format type that has been requested to the HTTP(Post) protocol supports the Text format and Binary format.The main format of text types include json, xml, soap, flatand etc. and Binary format(byte array) can include image files and etc.

4.Rest 

Using the Rest method, the client calls the biz class via URL as shown in the following. For example, if the client requests http://localhost:8080/hello/helloworld.txt from the server, bizId becomes helloworld and the message extension becomes txt. “Okeydokey-backend” java framework locates the annotated class using bizId and converts the request message into text form(String) and sends out calls.

5.Simple development

 The business logic materialization developer only creates Biz class.Materialize Biz class and mark annotation(@Biz). Biz class is materialized from inheriting only the following two methods.


Only the two methods are called by the framework and via such simple procedure, it materializes business logic. These simple structures prevent developer error by making it unnecessary to decide on the method naming rule. In addition, as each class performs single function, the visibility of the source becomes extremely high.Furthermore, the “Okeydokey-backend” java framework is not formed via complicated xml setting and does not support complicated difficult architecture. The goal is to quickly build a high performance system through simple light framework.

@Before, @After, callBiz

the @Before, @After annotation, order process of Biz class can easily be constructed. Using this function, complicated business processes can easily be constructed. Calls regarding other Biz class are called through the provided callBiz method.


6.Open source

“Okeydokey-backend” java framework is an open source and adheres to the GNU(http://www.gnu.org/copyleft/gpl.html) license code.

