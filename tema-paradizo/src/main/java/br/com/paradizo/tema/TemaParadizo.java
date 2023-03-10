package br.com.paradizo.tema;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.plugin.Plugin;

/**
 * Esta classe é uma implementação (Service Provider) da {@link Plugin} (Service
 * Provider Interface), sendo responsável por estilizar os capítulos do
 * {@link Ebook} final.
 * 
 * @author MarcosGOFavaretto (marcosfavaretto.dev@gmail.com)
 *
 */
public class TemaParadizo implements Plugin {

	private static final String NOME_ARQUIVO_ESTILO = "/tema.css";

	/**
	 * Método responsável por obter o CSS (Cascading Style Sheets) do arquivo
	 * especificado na constante NOME_ARQUIVO_ESTILO.
	 * 
	 * @return todo o conteúdo do arquivo CSS encontrado.
	 */
	private String cssDoTema() {
		return FileUtils.getResourceContents(NOME_ARQUIVO_ESTILO);
	}

	/**
	 * Método responsável por adicionar, através do {@link Jsoup}, o conteúdo do CSS
	 * ao HTML do {@link Capitulo}.
	 * 
	 * @param html: Neste parâmetro, deve ser informado o HTML que será estilizado.
	 * @return o HTML informado com a adição da tag "style" e o CSS.
	 */
	private String aplicaTema(String html) {

		Document document = Jsoup.parse(html);
		String css = cssDoTema();

		document.select("head").append("<style> " + css + " </style>");

		return document.html();
	}

	/**
	 * Esta é a implementação que o service disponibiliza para o evento disparado
	 * após a renderização do {@link Capitulo} do {@link Ebook}.
	 * 
	 * @param html: Neste parâmetro, deve ser fornecido o HTML do {@link Capitulo}.
	 * @return o HTML informado com a adição do estilo.
	 */
	@Override
	public String aposRenderizacao(String html) {

		String htmlComTema = aplicaTema(html);
		return htmlComTema;

	}

	/**
	 * Método não implementado para este plugin.
	 */
	@Deprecated
	@Override
	public void aposGeracao(Ebook ebook) {
		return;
	}

}
