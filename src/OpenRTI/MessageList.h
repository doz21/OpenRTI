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

#ifndef OpenRTI_MessageList_h
#define OpenRTI_MessageList_h

#include <list>
#include "AbstractMessage.h"

namespace OpenRTI {

class MessageList {
public:
  bool empty() const
  { return _list.empty(); }
  void push_back(const SharedPtr<AbstractMessage>& message)
  {
    // Take the list entry from the pool if possible
    if (_pool.empty()) {
      _list.push_back(message);
    } else {
      _list.splice(_list.end(), _pool, _pool.begin());
      _list.back() = message;
    }
  }
  SharedPtr<AbstractMessage> pop_front()
  {
    if (_list.empty())
      return 0;
    // take away the front message and move the empty list entry to the pool.
    SharedPtr<AbstractMessage> message;
    message.swap(_list.front());
    _pool.splice(_pool.begin(), _list, _list.begin());
    return message;
  }

private:
  typedef std::list<SharedPtr<AbstractMessage> > List;
  List _list;
  List _pool;
};

} // namespace OpenRTI

#endif