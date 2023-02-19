/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 *
 * @author Gabry
 */
public class xmlJava_2 {
    
    private static final String FILENAME = "C://Users//20102//IdeaProjects//SOA_2//src//main//java//app2//Books2.xml";
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {

        int option = 0 ;
		System.out.println("**Welcome to XML Application**");
                System.out.println("------------------------------");
		System.out.println("1) Add new Books");
		System.out.println("2) Search for a Book");
                System.out.println("3) Delete Book by Id");
                System.out.println("4) Update Book Details");
                System.out.println("------------------------------");
		Scanner in = new Scanner(System.in);
		option = in.nextInt();
		switch (option) {
			case 1: {
				add_books();
				break;
			}
			case 2:{
				search_books();
				break;
			}
			case 3:{
				delete_book();
				break;
			}
                        case 4: {
                                update_book();
                                break;
                        }
		}  
    }
    

    private static boolean idExists(Document xmlDoc, String id) {
        NodeList bookList = xmlDoc.getElementsByTagName("Book");
        for (int i = 0; i < bookList.getLength(); i++) {
            Element book = (Element) bookList.item(i);
            if (book.getAttribute("id").equals(id)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    
    public static int is_new = 0;
    public static void add_books() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException  {
    
    System.out.println("Enter number of books : ");
    Scanner in = new Scanner(System.in);
    int num= in.nextInt();
    in.nextLine(); // Advance the scanner to the next line
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    
    Document xmlDoc=null;     //CREATE Doc
    //CREATE root
    Element root=null;     //Create root
    
//        if(is_new==0){ //if document is free
//            xmlDoc=docBuilder.newDocument();
//            root=xmlDoc.createElement("Catalogue");
//            xmlDoc.appendChild(root);
//            is_new=1;
//        }
//        else
//        {
            xmlDoc=docBuilder.parse("C://Users//20102//IdeaProjects//SOA_2//src//main//java//app2//Books2.xml");
            root=xmlDoc.getDocumentElement();
//        }
    for(int i=0;i<num;i++)
    {
         Element book = xmlDoc.createElement("Book");
         root.appendChild(book);//add book to root(Catalogue)
         
        //id
         System.out.println("Adding the Book #"+(i+1));
         System.out.println("Add Book ID:");
         String id = in.nextLine();
        if (id.isEmpty()) {
            System.out.println("Error: ID cannot be empty");
            return;
        }
        if (idExists(xmlDoc, id)) {
            System.out.println("Error: ID already exists");
            return;
        }
        book.setAttribute("id", id);


        //Author
        String author = "";
        while (true) {
            System.out.println("Add the Author: ");
            author = in.nextLine();
            if (author.isEmpty()) {
                System.out.println("Error: Author cannot be empty");
            } else if (!author.matches("^[a-zA-Z]+$")) {
                System.out.println("Error: Author name can only contain characters (a-z)");
            } else {
                break;
            }
        }
        Element authorElem = xmlDoc.createElement("Author");
        authorElem.appendChild(xmlDoc.createTextNode(author));
        book.appendChild(authorElem); 
        
        //Title
        String title = "";
        while (true) {
            System.out.println("Add the Title: ");
            title = in.nextLine();
            if (title.isEmpty()) {
                System.out.println("Error: Title cannot be empty");
            } else {
                break;
            }
        }
        Element titleElem = xmlDoc.createElement("Title");
        titleElem.appendChild(xmlDoc.createTextNode(title));
        book.appendChild(titleElem);
         
        //Genre
        String genre = "";
        while (true) {
            System.out.println("Add the Genre: ");
            genre = in.nextLine();
            if (genre.isEmpty()) {
                System.out.println("Error: Genre cannot be empty");
            } else if (!genre.equals("Science") && !genre.equals("Fiction") && !genre.equals("Drama")) {
                System.out.println("Error: Genre must be one of the following: Science, Fiction, Drama");
            } else {
                break;
            }
        }
        Element genreElem = xmlDoc.createElement("Genre");
        genreElem.appendChild(xmlDoc.createTextNode(genre));
        book.appendChild(genreElem);
         
        //Price
        String priceString = "";
        double price = 0;
        while (true) {
            System.out.println("Add the Price: ");
            priceString = in.nextLine();
            if (priceString.isEmpty()) {
                System.out.println("Error: Price cannot be empty");
            } else {
                try {
                    price = Double.parseDouble(priceString);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Price must be a valid number");
                }
            }
        }
        Element priceElem = xmlDoc.createElement("Price");
        priceElem.appendChild(xmlDoc.createTextNode(Double.toString(price)));
        book.appendChild(priceElem);
         
        //Publish Date
        String publishDate = "";
        while (true) {
            System.out.println("Add the Publish Date (yyyy-mm-dd): ");
            publishDate = in.nextLine();
            if (publishDate.isEmpty()) {
                System.out.println("Error: Publish date cannot be empty");
            } else if (!isValidDateFormat(publishDate)) {
                System.out.println("Error: Publish date must be a valid date in the format yyyy-mm-dd");
            } else {
                break;
            }
        }
        Element publishDateElem = xmlDoc.createElement("Publish_Date");
        publishDateElem.appendChild(xmlDoc.createTextNode(publishDate));
        book.appendChild(publishDateElem);
         
        //Description
        System.out.println("Add the Description (optional): ");
        String description = in.nextLine();
        Element descriptionElem = xmlDoc.createElement("Description");
        descriptionElem.appendChild(xmlDoc.createTextNode(description));
        book.appendChild(descriptionElem);
    
         

        //WRITE TO XML FILE
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(xmlDoc);
        StreamResult result = new StreamResult(new File(FILENAME));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("Book(s) added successfully to the catalogue");
    }
      
    }
    
    public static void search_books() throws ParserConfigurationException, SAXException, IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the attribute you want to search by (Author, Title, Genre, Price, Publish_Date, Description): ");
        String attribute = in.nextLine();

        System.out.println("Enter the value of the attribute you want to search for: ");
        String value = in.nextLine();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document xmlDoc = docBuilder.parse("C://Users//20102//IdeaProjects//SOA_2//src//main//java//app2//Books2.xml");

        Element root = xmlDoc.getDocumentElement();

        NodeList bookList = root.getElementsByTagName("Book");

        // Initialize a counter to track the number of matching books
        int numMatchingBooks = 0;

        for (int i = 0; i < bookList.getLength(); i++) {
            Node bookNode = bookList.item(i);

            // Check if the node is a "Book" element
            if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                Element bookElement = (Element) bookNode;

                String attributeValue = bookElement.getElementsByTagName(attribute).item(0).getTextContent();

                if (attributeValue.equals(value)) {
                    
                    numMatchingBooks++;
                    System.out.println("Book " + numMatchingBooks + ": ");
                    System.out.println("Author: " + bookElement.getElementsByTagName("Author").item(0).getTextContent());
                    System.out.println("Title: " + bookElement.getElementsByTagName("Title").item(0).getTextContent());
                    System.out.println("Genre: " + bookElement.getElementsByTagName("Genre").item(0).getTextContent());
                    System.out.println("Price: " + bookElement.getElementsByTagName("Price").item(0).getTextContent());
                    System.out.println("Publish Date: " + bookElement.getElementsByTagName("Publish_Date").item(0).getTextContent());
                    System.out.println("Description: " + bookElement.getElementsByTagName("Description").item(0).getTextContent());
                    System.out.println();
                }
            }
        }
        if (numMatchingBooks == 0) {
            System.out.println("No books found with " + attribute + " equal to " + value + ".");
        } else {
            System.out.println(numMatchingBooks + " book(s) found with " + attribute + " equal to " + value + ".");
        }
    }

    public static void delete_book() throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException{
            Scanner in = new Scanner(System.in);
            System.out.println("Enter ID of Book: ");
            String search = in.nextLine();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(FILENAME));

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer tFormer = tFactory.newTransformer();

            NodeList list = doc.getElementsByTagName("Book"); //to get all items from book(author,title....)
            for (int i = 0; i < list.getLength(); i++){
               Node node=list.item(i); //get first book
               if (node.getNodeType() == Node.ELEMENT_NODE){
                  Element element = (Element) node;
                  // get attribute of book
                  String id = element.getAttribute("id");

                  if(id.equalsIgnoreCase(search)){
                     element.getParentNode().removeChild(element);
                     //  Normalize the DOM tree to combine all adjacent nodes
                     doc.normalize();
                     Source source = new DOMSource(doc);

                     tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
                     FileOutputStream output = new FileOutputStream("C://Users//20102//IdeaProjects//SOA_2//src//main//java//app2//Books2.xml");
                     StreamResult result = new StreamResult(output);
                     tFormer.transform(source, result);
                   }
               }
            }   
    }


    public static void update_book() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter the id of the book to be updated: ");
        String id = in.nextLine();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document xmlDoc = docBuilder.parse(FILENAME);
        Element root = xmlDoc.getDocumentElement();
        NodeList bookList = root.getElementsByTagName("Book");
        Element book = null;
        for (int i = 0; i < bookList.getLength(); i++) {
            Element b = (Element) bookList.item(i);
            if (b.getAttribute("id").equals(id)) {
                book = b;
                break;
            }
        }

        if (book == null) {
            System.out.println("Book with id " + id + " not found.");
            return;
        }

        System.out.println("Enter the updated values for the book's attributes (leave blank to preserve existing value):");
        
        //Author
        String author;
        while (true) {
            System.out.println("Author: ");
            author = in.nextLine();
            if (author.isEmpty()) {
                break;
            }
            if (author.matches("^[a-zA-Z]+$")) {
                book.getElementsByTagName("Author").item(0).setTextContent(author);
                break;
            } else {
                System.out.println("Invalid author name. Author name must contain characters (a-z) only.");
            }
        }
        
        System.out.println("Title: ");
        String title = in.nextLine();
        if (!title.isEmpty()) {
            book.getElementsByTagName("Title").item(0).setTextContent(title);
        }
        
        //Genre
        String genre;
        while (true) {
            System.out.println("Genre: ");
            genre = in.nextLine();
            if (genre.isEmpty()) {
                break;
            }
            if (genre.equals("Science") || genre.equals("Fiction") || genre.equals("Drama")) {
                book.getElementsByTagName("Genre").item(0).setTextContent(genre);
                break;
            } else {
                System.out.println("Invalid genre. Genre must be one of the following: Science, Fiction, Drama.");
            }
        }
        
        //Price
        String price;
        while (true) {
            System.out.println("Price: ");
            price = in.nextLine();
            if (price.isEmpty()) {
                break;
            }
            if (price.matches("^[+-]?([0-9]*[.])?[0-9]+$")) {
                book.getElementsByTagName("Price").item(0).setTextContent(price);
                break;
            } else {
                System.out.println("Invalid price. Price must be a double.");
            }
        }
  
        // Publish date
        String publishDate;
        while (true) {
            System.out.println("Publish Date: ");
            publishDate = in.nextLine();
            if (publishDate.isEmpty()) {
                break;
            }
            if (publishDate.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
                book.getElementsByTagName("Publish_Date").item(0).setTextContent(publishDate);
                break;
            } else {
                System.out.println("Invalid publish date. Publish date must be in the format yyyy-mm-dd.");
            }
        }

        //Description
        System.out.println("Description: ");
        String description = in.nextLine();
        if (!description.isEmpty()) {
            book.getElementsByTagName("Description").item(0).setTextContent(description);
        }

        // Write the updated XML document to the file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(xmlDoc);
        StreamResult result = new StreamResult(new File(FILENAME));
        transformer.transform(source, result);

        System.out.println("Book with id " + id + " updated successfully.");
    }   
}