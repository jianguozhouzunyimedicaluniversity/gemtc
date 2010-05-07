/*
 * This file is part of drugis.org MTC.
 * MTC is distributed from http://drugis.org/mtc.
 * Copyright (C) 2009-2010 Gert van Valkenhoef.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.drugis.mtc.yadas

import org.scalatest.junit.ShouldMatchersForJUnit
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import org.drugis.mtc._

class ContinuousDataIT extends ShouldMatchersForJUnit {
	// data from Welton et. al., Am J Epidemiol 2009;169:1158–1165
	val m = -1.362791 // mean(d)
	val s = 0.982033 // sd(d)
	val f = 0.05

	def network = {
		val is = classOf[ContinuousDataIT].getResourceAsStream("weltonBP.xml")
		Network.contFromXML(scala.xml.XML.load(is))
	}

	val psych = new Treatment("psych")
	val usual = new Treatment("usual")

	@Test def testResult() {
		val model = new YadasConsistencyModel(network)
		model.run()
		
		model.isReady should be (true)
		val d = model.getRelativeEffect(usual, psych)
		d.getMean should be (m plusOrMinus f * s)
		d.getStandardDeviation should be (s plusOrMinus f * s)
	}
}
