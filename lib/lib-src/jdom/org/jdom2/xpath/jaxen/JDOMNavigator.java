/*--

 Copyright (C) 2011-2012 Jason Hunter & Brett McLaughlin.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The name "JDOM" must not be used to endorse or promote products
    derived from this software without prior written permission.  For
    written permission, please contact <request_AT_jdom_DOT_org>.

 4. Products derived from this software may not be called "JDOM", nor
    may "JDOM" appear in their name, without prior written permission
    from the JDOM Project Management <request_AT_jdom_DOT_org>.

 In addition, we request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by the
      JDOM Project (http://www.jdom.org/)."
 Alternatively, the acknowledgment may be graphical using the logos
 available at http://www.jdom.org/images/logos.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 This software consists of voluntary contributions made by many
 individuals on behalf of the JDOM Project and was originally
 created by Jason Hunter <jhunter_AT_jdom_DOT_org> and
 Brett McLaughlin <brett_AT_jdom_DOT_org>.  For more information
 on the JDOM Project, please see <http://www.jdom.org/>.

 */

package org.jdom2.xpath.jaxen;

import java.util.HashMap;
import java.util.List;

import org.jaxen.NamespaceContext;

import org.jdom2.Namespace;
import org.jdom2.NamespaceAware;

final class JDOMNavigator extends JDOMCoreNavigator implements NamespaceContext {

	/**
	 * Standard JDOM2 Serialization. Default mechanism.
	 */
	private static final long serialVersionUID = 200L;
	
	private final HashMap<String, String> nsFromContext = new HashMap<String, String>();
	private final HashMap<String, String> nsFromUser = new HashMap<String, String>();

	@Override
	void reset() {
		super.reset();
		nsFromContext.clear();
	}

	void setContext(Object node) {
		nsFromContext.clear();

		List<Namespace> nsl = null;
		if (node instanceof NamespaceAware) {
			nsl = ((NamespaceAware)node).getNamespacesInScope();
		} else if (node instanceof NamespaceContainer) {
			nsl = ((NamespaceContainer)node).getParentElement().getNamespacesInScope();
		}
		if (nsl != null) {
			for (Namespace ns : nsl) {
				nsFromContext.put(ns.getPrefix(), ns.getURI());
			}
		}
	}

	void includeNamespace(Namespace namespace) {
		nsFromUser.put(namespace.getPrefix(), namespace.getURI());
	}

	@Override
	public String translateNamespacePrefixToUri(String prefix) {
		if (prefix == null) {
			return null;
		}
		String uri = nsFromUser.get(prefix);
		if (uri != null) {
			return uri;
		}
		return nsFromContext.get(prefix);
	}

}