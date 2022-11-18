package cotuba.pdf;

import cotuba.domain.Ebook;

public interface GeradorPDF {

	void gera(Ebook ebook);

	/*
	 * Este método foi criado para simular uma Factory, para que outro arquivo não
	 * precisasse ser criado.
	 */
	static GeradorPDF cria() {
		return new GeradorPDFComIText();
	}
}
