package cotuba.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import cotuba.application.ParametrosCotuba;
import cotuba.domain.FormatoEbook;

class LeitorOpcoesCLI implements ParametrosCotuba {

	private Path diretorioDosMD;
	private FormatoEbook formato;
	private Path arquivoDeSaida;
	private boolean modoVerboso;

	public LeitorOpcoesCLI(String[] args) {
		Options options = criaOpcoes();
		CommandLine cmd = parseDosArgumentos(args, options);
		trataDiretorioDosMD(cmd);
		trataFormato(cmd);
		trataArquivoDeSaida(cmd);
		trataModoVerboso(cmd);
	}

	private Options criaOpcoes() {
		Options options = new Options();

		var opcaoDeDiretorioDosMD = new Option("d", "dir", true,
				"Diretório que contém os arquivos md. Default: diretório atual.");
		options.addOption(opcaoDeDiretorioDosMD);

		var opcaoDeFormatoDoEbook = new Option("f", "format", true,
				"Formato de saída do ebook. Pode ser: pdf, epub ou html. Default: pdf");
		options.addOption(opcaoDeFormatoDoEbook);

		var opcaoDeArquivoDeSaida = new Option("o", "output", true,
				"Arquivo de saída do ebook. Default: book.{formato}.");
		options.addOption(opcaoDeArquivoDeSaida);

		var opcaoModoVerboso = new Option("v", "verbose", false, "Habilita modo verboso.");
		options.addOption(opcaoModoVerboso);

		return options;
	}

	private CommandLine parseDosArgumentos(String[] args, Options options) {
		CommandLineParser cmdParser = new DefaultParser();
		var ajuda = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = cmdParser.parse(options, args);
		} catch (ParseException e) {
			ajuda.printHelp("cotuba", options);
			throw new IllegalArgumentException("Opção inválida", e);
		}

		return cmd;
	}

	private void trataDiretorioDosMD(CommandLine cmd) {
		String nomeDoDiretorioDosMD = cmd.getOptionValue("dir");

		if (nomeDoDiretorioDosMD != null) {
			diretorioDosMD = Paths.get(nomeDoDiretorioDosMD);
			if (!Files.isDirectory(diretorioDosMD)) {
				throw new IllegalArgumentException(nomeDoDiretorioDosMD + " não é um diretório.");
			}
		} else {
			Path diretorioAtual = Paths.get("");
			diretorioDosMD = diretorioAtual;
		}
	}

	private void trataFormato(CommandLine cmd) {
		String nomeDoFormatoDoEbook = cmd.getOptionValue("format");

		if (nomeDoFormatoDoEbook != null) {
			formato = FormatoEbook.valueOf(nomeDoFormatoDoEbook.toUpperCase());
		} else {
			formato = FormatoEbook.PDF;
		}
	}

	private void trataArquivoDeSaida(CommandLine cmd) {
		String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue("output");
		if (nomeDoArquivoDeSaidaDoEbook != null) {
			arquivoDeSaida = Paths.get(nomeDoArquivoDeSaidaDoEbook);
		} else {
			arquivoDeSaida = Paths.get("book." + formato.name().toLowerCase());
		}

		try {
			if (Files.isDirectory(arquivoDeSaida)) {
				// deleta arquivos do diretório recursivamente
				Files.walk(arquivoDeSaida).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
			} else {
				Files.deleteIfExists(arquivoDeSaida);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void trataModoVerboso(CommandLine cmd) {
		modoVerboso = cmd.hasOption("verbose");
	}

	@Override
	public Path getDiretorioDosMD() {
		return diretorioDosMD;
	}

	@Override
	public FormatoEbook getFormato() {
		return formato;
	}

	@Override
	public Path getArquivoDeSaida() {
		return arquivoDeSaida;
	}

	public boolean isModoVerboso() {
		return modoVerboso;
	}

}
