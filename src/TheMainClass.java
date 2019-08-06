import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dobrynin91.Util.HttpConnectionClass;
import ru.dobrynin91.Util.StaxParserClass;
import ru.dobrynin91.entities.ModelCarsXml;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TheMainClass {

    private static final String URL = "http://autoload.avito.ru/format/Models.xml";

    public static void main(String[] args) {
        try {
            String response = HttpConnectionClass.getHttpResponse(URL);
            List<ModelCarsXml> modelXmlList = StaxParserClass.getParsedModelCarsXml(response);
            Map<String, List<String>> modelMap = modelXmlList.stream()
                    .filter(mdl -> mdl.getVendor() != null && mdl.getModel() != null)
                    .collect(Collectors.groupingBy(ModelCarsXml::getVendor,
                            Collectors.mapping(ModelCarsXml::getModel, Collectors.toList())));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelMap);
            System.out.println(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
