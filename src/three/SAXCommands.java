package three;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.xml.transform.TransformerConfigurationException;

import one.ProjectException;

import org.xml.sax.Attributes;

import org.xml.sax.SAXException;

import org.xml.sax.XMLReader;

import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.helpers.XMLReaderFactory;

import two.BooleanField;
import two.CharField;
import two.DateField;
import two.Field;
import two.IntField;
import two.RealField;
import two.Table;
import two.TableCollection;
import two.VarcharField;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This is responsible for handling the xml file
 *
 */
public class SAXCommands {
	private Table table;
	public static void main(String[] args) throws SAXException, IOException,
			TransformerConfigurationException {

		new SAXCommands().saxReader();

	}
/**
 * Reads from the xml file
 * @throws SAXException
 * @throws IOException
 */
	public void saxReader() throws SAXException, IOException {

		XMLReader reader = XMLReaderFactory.createXMLReader();

		reader.setContentHandler(new ExampleContentHandler());

		reader.parse("data.xml");

	}
/**
 * 
 * Handles the events when reading from the xml file
 *
 */
	private class ExampleContentHandler extends DefaultHandler {

		
		private boolean isField = false;
		private boolean isName = false;
		private boolean isType = false;
		private boolean isSize = false;
		private String name;
		private String type;
		private int size;

		

		@Override
		public void startDocument() throws SAXException {

			

		}

		@Override
		public void endDocument() throws SAXException {

			

		}

		/**
		 * Start of the event
		 */

		@Override
		public void startElement(String uri, String localName, String qName,

		Attributes attributes) throws SAXException {

			
			
			if (qName.equalsIgnoreCase("field")) {

				isField = true;

			}
			if (qName.equalsIgnoreCase("name")) {

				isName = true;

			}
			if (qName.equalsIgnoreCase("type")) {

				isType = true;

			}
			if (qName.equalsIgnoreCase("size")) {

				isSize = true;

			}
			File dir = new File("directory");
			dir.mkdir();
			for (int i = 0; i < attributes.getLength(); i++) {
			
				
			
					try {
						table = new Table(attributes.getValue(i));
						TableCollection.get().add(attributes.getValue(i), table);
					} catch (ProjectException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			
			}

		}
/**
 * end of the event
 */
		@Override
		public void endElement(String uri, String localName, String qName)

		throws SAXException {

			
			
			if (qName.equalsIgnoreCase("field")) {
				
				isField = false;
				if (type.equalsIgnoreCase("char")) {
					table.add(new CharField(name, type, size));
					
				} else if(type.equalsIgnoreCase("integer")) {
					table.add(new IntField(name, type));
	
				}
				else if(type.equalsIgnoreCase("varchar")) {
					try {
						table.add(new VarcharField(name, type));
					} catch (ProjectException e) {
						
						e.printStackTrace();
					}
				}
				else if(type.equalsIgnoreCase("boolean")) {
					table.add(new BooleanField(name, type));
				}
				else if(type.equalsIgnoreCase("date")) {
					table.add(new DateField(name, type));
				}
				else if(type.equalsIgnoreCase("real")) {
					table.add(new RealField(name, type));
				}
				
				
			}
			if (qName.equalsIgnoreCase("name")) {

				isName = false;
			}
			if (qName.equalsIgnoreCase("type")) {

				isType = false;
			}
			if (qName.equalsIgnoreCase("size")) {

				isSize = false;
			}
		}
/**
 * Rewrites the character field from the xml
 */
		@Override
		public void characters(char[] ch, int start, int length)

		throws SAXException {

			String username = new String(ch, start, length); 
			

		

			
			
			if (isName) {
				name = new String(ch, start, length);
			

			}
			if (isType) {
				type = new String(ch, start, length);
			

			}
			if (isSize) {

				size = Integer.parseInt(new String(ch, start, length));
				

			}

		}

	}

}
