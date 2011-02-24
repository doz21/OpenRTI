/* -*-c++-*- OpenRTI - Copyright (C) 2009-2011 Mathias Froehlich
 *
 * This file is part of OpenRTI.
 *
 * OpenRTI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * OpenRTI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OpenRTI.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

#include "FDDFileReader.h"

#include <fstream>

#include "Exception.h"
#include "FDDContentHandler.h"
#include "Message.h"

namespace OpenRTI {

bool
readFDDFile(const std::wstring& fullPathNameToTheFDDfile, FOMStringModule& module)
{
  // Make sure we can read the fdd file
  std::ifstream fddStream(ucsToLocale(fullPathNameToTheFDDfile).c_str());
  if (!fddStream.is_open())
    throw CouldNotOpenFDD(fullPathNameToTheFDDfile);

  // FIXME: have a function for that
  // Set up the fdd parser
  SharedPtr<XML::XMLReader> reader;
  reader = new XML::ExpatXMLReader;

  SharedPtr<FEDContentHandler> contentHandler = new FEDContentHandler;
  reader->setContentHandler(contentHandler.get());
  SharedPtr<FEDErrorHandler> errorHandler = new FEDErrorHandler;
  reader->setErrorHandler(errorHandler.get());

  reader->parse(fddStream);

  std::wstring errorMessage = errorHandler->getMessages();
  if (!errorMessage.empty())
    throw ErrorReadingFDD(errorMessage);

  module = contentHandler->getFOMStringModule();
  return true;
}

} // namespace OpenRTI