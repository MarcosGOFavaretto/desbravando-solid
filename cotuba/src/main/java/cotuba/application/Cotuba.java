package cotuba.application;

import java.nio.file.Path;
import java.util.List;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.epub.GeradorEPUB;
import cotuba.md.RenderizadorMDParaHTML;
import cotuba.pdf.GeradorPDF;

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

		RenderizadorMDParaHTML renderizador = new RenderizadorMDParaHTML();
		List<Capitulo> capitulos = renderizador.renderiza(diretorioDosMD);

		Ebook ebook = new Ebook();
		ebook.setFormato(formato);
		ebook.setArquivoDeSaida(arquivoDeSaida);
		ebook.setCapitulos(capitulos);

		GeradorEbook geradorEbook;
		if ("pdf".equals(formato)) {
			geradorEbook = new GeradorPDF();
			geradorEbook.gera(ebook);

		} else if ("epub".equals(formato)) {
			geradorEbook = new GeradorEPUB();
			geradorEbook.gera(ebook);

		} else {
			throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
		}
	}

}
