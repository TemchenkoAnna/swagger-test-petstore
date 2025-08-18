package swagger;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {
    String baseUrl();

    String locale();

    boolean logging();
}
