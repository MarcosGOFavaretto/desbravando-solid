package cotuba.application;

import java.nio.file.Path;
import java.util.List;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;

/**
 * Esta classe é um caso de uso. A sua função é orquestar a execução das demais
 * classes Modelo, para que a saída esperada seja atingida através dos dados
 * recebidos.
 * 
 * Dentro da arquitetura MVC, ela se encaixaria dentro de M (Model), visto que
 * não apresenta componentes de UI e não realiza quaisquer interações com o V
 * (View).
 * 
 * @author MarcosGOFavaretto (marcosfavaretto.dev@gmail.com)
 *
 */
public class Cotuba {

	public void executa(ParametrosCotuba parametros) {

		Path diretorioDosMD = parametros.getDiretorioDosMD();
		String formato = parametros.getFormato();
		Path arquivoDeSaida = parametros.getArquivoDeSaida();

		RenderizadorMDParaHTML renderizador = RenderizadorMDParaHTML.cria();
		List<Capitulo> capitulos = renderizador.renderiza(diretorioDosMD);

		Ebook ebook = new Ebook();
		ebook.setFormato(formato);
		ebook.setArquivoDeSaida(arquivoDeSaida);
		ebook.setCapitulos(capitulos);

		if ("pdf".equals(formato)) {
			GeradorPDF geradorPDF = GeradorPDF.cria();
			geradorPDF.gera(ebook);

		} else if ("epub".equals(formato)) {
			GeradorEPUB geradorEPUB = GeradorEPUB.cria();
			geradorEPUB.gera(ebook);

		} else {
			throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
		}
	}

}
