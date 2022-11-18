package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.epub.GeradorEPUBComEpublib;

public interface GeradorEPUB {

	void gera(Ebook ebook);

	/*
	 * Este método foi criado para simular uma Factory, para que outro arquivo não
	 * precisasse ser criado.
	 */
	static GeradorEPUB cria() {
		return new GeradorEPUBComEpublib();
	}

}
