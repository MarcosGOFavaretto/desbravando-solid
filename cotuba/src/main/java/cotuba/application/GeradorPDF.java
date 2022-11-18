package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.pdf.GeradorPDFComIText;

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
