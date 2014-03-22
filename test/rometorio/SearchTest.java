/**
 * 
 */
package rometorio;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.SearchResult;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author tpearson
 *
 */
public class SearchTest {

	/**
	 * Test method for {@link rometorio.Search#parseResponse(com.fasterxml.jackson.databind.JsonNode, java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testParseResponse() throws JsonProcessingException, IOException {
		JsonNode response = new ObjectMapper().readTree(new File("D:\\jdevgit\\long-layover\\test\\search-response.json"));
		List<SearchResult> results = Search.parseResponse(response, "BOS", "MOW");
		System.out.println(results);
		Assert.assertNotNull(results);
	}

}
