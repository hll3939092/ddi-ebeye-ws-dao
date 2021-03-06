package uk.ac.ebi.ddi.ebe.ws.dao.client.facet;

import org.springframework.web.util.UriComponentsBuilder;
import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.facet.FacetList;
import uk.ac.ebi.ddi.ebe.ws.dao.utils.Constans;

import java.net.URI;

/**
 * @author Yasset Perez-Riverol ypriverol
 */

public class FacetWsClient extends EbeyeClient {

    /**
     * Default constructor for Ws clients
     *
     * @param config
     */
    public FacetWsClient(AbstractEbeyeWsConfig config) {
        super(config);
    }

    /**
     * Retrieve for a parent domain and a set of subdomains the number of entries for an specific facet
     * @param parentdomain parent domain
     * @param domains      subdomains
     * @param facetField   the facet field
     * @return Return a facet field with the information of the term
     */
    public FacetList getFacetEntriesByDomains(String parentdomain, String[] domains, String facetField, int count) {

        String domain = String.join(" " + Constans.OR + " ", domains);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(config.getProtocol())
                .host(config.getHostName())
                .path("/ebisearch/ws/rest")
                .path("/" + parentdomain)
                .queryParam("query", "domain_source:(" + domain + ")")
                .queryParam("facetfields", facetField)
                .queryParam("facetcount", count)
                .queryParam("size", "0")
                .queryParam("format", "JSON");

        URI uri = builder.build().encode().toUri();
        return restTemplate.getForObject(uri, FacetList.class);
    }

}
