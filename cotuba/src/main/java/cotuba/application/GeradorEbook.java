package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.epub.GeradorEPUB;

public interface GeradorEbook {

	void gera(Ebook ebook);

	/*
	 * Este método foi criado para simular uma Factory, para que outro arquivo não
	 * precisasse ser criado.
	 */
	static GeradorEbook cria() {
		return new GeradorEPUB();
	}

}
