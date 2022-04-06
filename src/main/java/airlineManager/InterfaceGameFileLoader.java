package airlineManager;

import java.util.List;

public interface InterfaceGameFileLoader<GameObjects> {

    List<GameObjects> load(String filename);

}
