/**
 * High cohesion: the class is responsible for read the file XML
 */
package BD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sistema.Disciplina;

/**
 * @author FELIPE
 */
public class xmlParser {

	private static List<Disciplina> listDisciplinas = new ArrayList<Disciplina>();
	
	/**
	 * upload disciplines
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void uploadDisciplas() throws ParserConfigurationException, SAXException, IOException {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		
		Document doc = docment();
		NodeList nodeList = doc.getElementsByTagName("disciplina");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				listDisciplinas.add(criaObjeto(nNode));
			}
		}
	}
	
	/**
	 * create and return object of type Disciplina
	 */
	private static Disciplina criaObjeto(Node nNode) {
		Disciplina disciplina = new Disciplina();
		
		Element disciplinaXml = (Element) nNode;
		
		String id = disciplinaXml.getAttribute("id");
		String nome = disciplinaXml.getAttribute("nome");
		int dificuldade = Integer.parseInt(disciplinaXml.getElementsByTagName("dificuldade").item(0).getTextContent());
		int creditos = Integer.parseInt(disciplinaXml.getElementsByTagName("creditos").item(0).getTextContent());
		int periodo = Integer.parseInt(disciplinaXml.getElementsByTagName("periodo").item(0).getTextContent());
		String[] prerequisitos = disciplinaXml.getElementsByTagName("prerequisitos").item(0).getTextContent().split(";");

		//SETANDO OS ATRIBUTOS DA CADEIRA
		disciplina.setCredits(creditos);
		disciplina.setDificult(dificuldade);
		disciplina.setName(nome);
		disciplina.setPeriod(periodo);
		disciplina.setID(id);
		if(!prerequisitos[0].equals(""))
			disciplina.setPrerequisitos(prerequisitos);
		
		return disciplina;
	}
	
	/**
	 * Cria um parser XML
	 */
	private static Document docment() throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("disciplinas.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	/**
	 * Return list with all disciplines
	 * @return List with all disciplines
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static List<Disciplina> getListOfDiscipline() throws ParserConfigurationException, SAXException, IOException {
		if (listDisciplinas.isEmpty()) {
			uploadDisciplas();
		}
		return listDisciplinas;
	}
}

