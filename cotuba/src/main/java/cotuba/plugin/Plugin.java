package cotuba.plugin;

import java.util.ServiceLoader;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;

/**
 * Esta interface é a SPI (Service Provider Interface), que irá permitir a
 * criação de plugins que possam extender as funções do Cotuba.
 * 
 * @author MarcosGOFavaretto (marcosfavaretto.dev@gmail.com)
 *
 */
public interface Plugin {

	/**
	 * Este atributo da interface foi criado para evitar que o {@link ServiceLoader}
	 * faça mais do que uma busca no classpath em busca de implementações desta SPI.
	 * Este foi um dos desafios sugeridos pelo autor do livro.
	 * 
	 */
	static ServiceLoader<Plugin> plugins = ServiceLoader.load(Plugin.class);

	/**
	 * Este método deve ser implementado pelos plugins. Será executado sempre após a
	 * renderização de cada um dos capítulos do ebook.
	 * 
	 * @param html: Neste parâmetro, deve ser fornecido o HTML renderizado para o
	 *              {@link Capitulo}.
	 * @return Espera-se que o retorno seja o mesmo HTML com os estilos (CSS)
	 *         aplicados. O retorno deste método é aplicado no Ebook final como
	 *         sendo o seu conteúdo.
	 */
	String aposRenderizacao(String html);

	/**
	 * Este método deve ser implementado pelos plugins. Será executado sempre após a
	 * geração do {@link Ebook}.
	 * 
	 * @param ebook: Neste parâmetro, deve ser fornecido o {@link Ebook} gerado.
	 */
	void aposGeracao(Ebook ebook);

	/**
	 * Este método é responsável por executar a implementação feita nos plugins para
	 * a estilização de HTML nos ebooks.
	 * 
	 * @param capitulo: Neste parâmetro, deve ser fornecido o {@link Capitulo} que
	 *                  foi renderizado, para que o mesmo tenha os estilos
	 *                  aplicados.
	 */
	static void renderizou(Capitulo capitulo) {

		plugins.forEach(plugin -> {

			String html = capitulo.getConteudoHTML();
			String htmlModificado = plugin.aposRenderizacao(html);
			capitulo.setConteudoHTML(htmlModificado);

		});

	}

	/**
	 * Este método é responsável por executar a implementação feita nos plugins após
	 * a geração do Ebook.
	 * 
	 * @param ebook: Neste parâmetro, deve ser fornecido o {@link Ebook} gerado pelo
	 *               Cotuba.
	 */
	static void gerou(Ebook ebook) {

		plugins.forEach(plugin -> plugin.aposGeracao(ebook));

	}

}
