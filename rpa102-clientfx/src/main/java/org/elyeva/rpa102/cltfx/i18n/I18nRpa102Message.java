package org.elyeva.rpa102.cltfx.i18n;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Clase utilizada para la internacionalizacion (i18n) y localizacion (i10n) de
 * la aplicacion.
 * <p>
 * Se basa en un ResourceBundle que se apoya en un Properties (fichero de
 * cadenas indexadas mediante una clave unica). Hay un fichero de este tipo y
 * una clase como esta por cada modulo de CVS que este internacionalizado.
 * <p>
 * <b> Ejemplo de uso: </b>
 * <code>String msg = Messages.getString("Aceptar");  </code>
 * <p>
 * Se pueden usar cadenas con formato y parametros para evitar crear multiples
 * cadenas donde en realidad solo hay un mensaje. Por ejemplo, podemos usar la
 * siguiente cadena con dos parametros formateados:
 * <p>
 * <code> "Hay {1,number,integer} entidades modificadas el dia {2,date}" </code>
 * <p>
 * Y luego en el codigo se utiliza la siguiente sentencia para darle valor a los
 * parametros:
 * <p>
 * <code> String msg = Messages.getString("MensajeEntidades", { new Integer(4), new Date() });  </code>
 * <p>
 * Para obtener ayuda del formato que deben tener estas cadenas, consultar el
 * javadoc de la clase {@link java.text.MessageFormat}.
 * <p>
 * Todos los componentes de la aplicacion que muestren mensajes, cadenas, etc
 * van a ir a buscar las cadenas a un fichero denominado:
 * <p>
 * <i>[nombre_modulo_cvs]_[codigo_idioma]_[codigo_pais]</i>
 * <p>
 * Por ejemplo: <code>bdiv10_es_ES.properties</code>
 * <p>
 * En Java, un fichero de este tipo solo admite caracteres ASCII por lo que para
 * utilizar caracteres Unicode en las cadenas (acentos, cirilico, asiatico, etc)
 * es necesario utilizar su correspondiente codigo Unicode con \\uXXXX.
 * <p>
 * Por supuesto existen editores de texto, plugins para el Eclipse o la utilidad
 * del JDK native2ascii.exe para convertir el texto Unicode a caracteres \\uXXXX
 * y viceversa.
 * <p>
 * Para mas informacion acerca de la internacionalizacion y localizacion con
 * Java:
 * <ul>
 * <li><a href="http://java.sun.com/docs/books/tutorial/i18n/">Java Tutorial -
 * Internationalization trail</a></li>
 * <li><a href="http://java.sun.com/j2se/corejava/intl/">Core Java
 * Internationalization</a></li>
 * <li><a href="http://java.sun.com/j2se/corejava/intl/reference/faqs/">Java
 * Internationalization FAQ</a></li>
 * </ul>
 *
 * @see java.util.Properties
 * @see java.util.ResourceBundle
 * @see java.util.Locale
 * @author UF768189
 * @version $Revision: 2477 $
 */
public final class I18nRpa102Message {
	/** ResourceBundle: se basa en un fichero (properties). */
	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		String bundleName = I18nRpa102Message.class.getPackage().getName() + ".rpa102";

		RESOURCE_BUNDLE = ResourceBundle.getBundle(bundleName);
	}


	public static String getString(Class<?> clasz, String key) {
		return getString(clasz.getName() + "." + key);
	}

	public static String getString(Class<?> clasz, String key, Object [] params) {
		return getString(clasz.getName() + "." + key, params);
	}

	/**
	 * Devuelve una cadena "localizada" de acuerdo al Locale de la JVM.
	 *
	 * @param key  Clave unica utilizada para identificar la cadena dentro del fichero de
	 *             cadenas (properties).
	 * @return  La cadena buscada "localizada" de acuerdo al Locale de la JVM.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}


	/**
	 * Devuelve una cadena con formato "localizada" de acuerdo al Locale de la JVM.
	 * Para obtener ayuda del formato que deben tener las cadenas, consultar el javadoc
	 * de la clase {@link java.text.MessageFormat}.
	 * @param key  Clave unica utilizada para identificar la cadena dentro del fichero de
	 *             cadenas (properties).
	 * @param params Lista de objetos cuyo valor se utiliza en la cadena en los lugares
	 *               indicados como {1},{2}....
	 * @see  	java.text.MessageFormat
	 * @return  La cadena buscada "localizada" de acuerdo al Locale de la JVM.
	 */
	public static String getString(String key, Object[] params) {
		try {
			String formatString = RESOURCE_BUNDLE.getString(key);
			MessageFormat mf = new MessageFormat(formatString);
			return mf.format(params);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Utility class constructor.
	 */
	private I18nRpa102Message() {
		// empty method
	}
}