package cotuba.application;

import java.nio.file.Path;
import java.util.List;

import cotuba.domain.Capitulo;

public interface RenderizadorMDParaHTML {

	List<Capitulo> renderiza(Path diretorioDosMD);

	/*
	 * Este método foi criado para simular uma Factory, para que outro arquivo não
	 * precisasse ser criado.
	 */
	static RenderizadorMDParaHTML cria() {
		return new RenderizadorMDParaHTMLComCommonMark();
	}
}
