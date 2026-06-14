// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.wizard.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AbstractTagTest {
	@Test
	void escapeHtmlEscapesMarkupAndQuotes() {
		assertEquals("&lt;script&gt;&amp;&quot;&#x27;&lt;/script&gt;", AbstractTag.escapeHtml("<script>&\"'</script>"));
	}

	@Test
	void escapeHtmlReturnsEmptyStringForNull() {
		assertEquals("", AbstractTag.escapeHtml(null));
	}
}
